package com.example.exercisemanager.ui.home

import com.example.exercisemanager.src.DisplayableItem
import com.example.exercisemanager.ui.exercises.Exercise
import com.example.exercisemanager.ui.groups.Group
import com.example.exercisemanager.ui.muscles.Muscle

enum class Order {
    Descending, Ascending
}

enum class TypeOrder {
    ExercisesFirst,
    GroupsFirst,
    Mixed
}

enum class Included {
    DisplayableItem,
    Exercise,
    Group
}

class Categories(var order: Order, var typeOrder: TypeOrder, var included: Included, var targetMuscles: MutableList<Muscle>) {

    fun applyCategories(unorderedList: MutableList<DisplayableItem>) : MutableList<DisplayableItem> {

        val groupList: MutableList<Group> = ArrayList()
        val exerciseList: MutableList<Exercise> = ArrayList()

        if (typeOrder != TypeOrder.Mixed) {
            for (item in unorderedList) {
                if (item is Exercise) {
                    exerciseList.add(item)
                } else {
                    groupList.add(item as Group)
                }
            }
            // type order exercises first is also default for Included.exercise
            if (included == Included.Exercise) {
                return (exerciseList as MutableList<DisplayableItem>)
                    .filterExercisesTargetMuscle(targetMuscles)
                    .sortAlphabetically(order)
            } else if (included == Included.Group) {
                return (groupList as MutableList<DisplayableItem>)
                    .filterGroupsTargetMuscle(targetMuscles)
                    .sortAlphabetically(order)
            } else {
                return if (typeOrder == TypeOrder.ExercisesFirst) {
                    ((exerciseList as MutableList<DisplayableItem>).sortAlphabetically(order) +
                     (groupList as MutableList<DisplayableItem>).sortAlphabetically(order))
                        .toMutableList()
                        .filterExercisesTargetMuscle(targetMuscles)
                        .filterGroupsTargetMuscle(targetMuscles)
                } else {
                    ((groupList as MutableList<DisplayableItem>).sortAlphabetically(order) +
                     (exerciseList as MutableList<DisplayableItem>).sortAlphabetically(order))
                        .toMutableList()
                        .filterExercisesTargetMuscle(targetMuscles)
                        .filterGroupsTargetMuscle(targetMuscles)
                }
            }
        }
        return unorderedList.filterExercisesTargetMuscle(targetMuscles)
            .filterGroupsTargetMuscle(targetMuscles).sortAlphabetically(order)
    }

    private fun MutableList<DisplayableItem>.sortAlphabetically(order: Order) : MutableList<DisplayableItem> {
        this.sortBy { it.name }
        if (order == Order.Descending) {
            this.reverse()
        }
        return this
    }

    private fun MutableList<DisplayableItem>.filterExercisesTargetMuscle(targetMuscles: MutableList<Muscle>)
    : MutableList<DisplayableItem>{
        if (targetMuscles.size == 0) {
            return this
        }
        val filteredList: MutableList<DisplayableItem> = ArrayList()
        for (item in this) {
            if (item is Exercise) {
                val targetMuscles = targetMuscles
                for (muscle in item.muscles) {
                    for (targetMuscle in targetMuscles) {
                        if (muscle == targetMuscle) {
                            targetMuscles.remove(muscle)
                            break
                        }
                    }
                    if (targetMuscles.isEmpty()) {
                        filteredList.add(item)
                        break
                    }
                }
            }
            else {
                filteredList.add(item)
            }
        }
        return filteredList
    }

    private fun MutableList<DisplayableItem>.filterGroupsTargetMuscle(targetMuscles: MutableList<Muscle>)
    : MutableList<DisplayableItem>{
        if (targetMuscles.size == 0) {
            return this
        }
        val filteredList: MutableList<DisplayableItem> = ArrayList()
        for (item in this) {
            if (item is Group) {
                val targetMuscles = targetMuscles
                for (exercise in item.exercises) {
                    for (muscle in exercise.muscles) {
                        for (targetMuscle in targetMuscles) {
                            if (muscle == targetMuscle) {
                                targetMuscles.remove(muscle)
                                break
                            }
                        }
                    }
                    if (targetMuscles.isEmpty()) {
                        filteredList.add(item)
                        break
                    }
                }
            }
            else {
                filteredList.add(item)
            }
        }
        return filteredList
    }
}