package napetrico.gen.arturroblox.utils.components

import javafx.geometry.Insets
import javafx.scene.control.*
import javafx.scene.layout.HBox
import javafx.scene.layout.Priority
import javafx.scene.layout.Region
import javafx.scene.layout.VBox
import javafx.stage.Stage
import napetrico.gen.arturroblox.models.CategoryModel
import napetrico.gen.arturroblox.models.NewCategory
import napetrico.gen.arturroblox.models.UpdateCategory
import napetrico.gen.arturroblox.viewmodels.CategoryViewModel

class CategoryFormComponent(
    existingCategory: CategoryModel? = null,
    private val categoryViewModel: CategoryViewModel
) : VBox() {
    private val descriptionField = TextField()
    private val descriptionErrorLabel = Label()
    private val colorPicker = ColorPicker()

    private val confirmButton = Button("Confirmar")
    private val cancelButton = Button("Cancelar")

    init {
        spacing = 12.0
        padding = Insets(16.0)

        setupDescriptionSection(existingCategory)
        setupColorSection(existingCategory)
        setupButtons(existingCategory)

        children.addAll(
            Label("Descrição"),

            VBox(
                4.0,
                descriptionField,
                descriptionErrorLabel
            ),

            Label("Cor"),

            colorPicker,

            createButtonRow()
        )
    }

    private fun setupDescriptionSection(
        existingCategory: CategoryModel?
    ) {

        existingCategory?.let {
            descriptionField.text = it.descriptionProperty.get()
        }

        descriptionErrorLabel.style =
            "-fx-text-fill: red;"

        descriptionErrorLabel.isManaged = false
        descriptionErrorLabel.isVisible = false
    }

    private fun setupColorSection(
        existingCategory: CategoryModel?
    ) {
        existingCategory?.let {
            colorPicker.value = it.colorProperty.get()
        }
    }

    private fun setupButtons(
        existingCategory: CategoryModel?
    ) {
        confirmButton.setOnAction {
            if (!validate()) {
                return@setOnAction
            }

            val description = descriptionField.text.trim()
            val selectedColor = colorPicker.value

            if (existingCategory == null) {
                categoryViewModel.create(NewCategory(description, selectedColor))
            } else {
                categoryViewModel.update(existingCategory, UpdateCategory(description, selectedColor))
            }

            closeWindow()
        }

        cancelButton.setOnAction {
            closeWindow()
        }
    }

    private fun validate(): Boolean {

        val description = descriptionField.text.trim()

        if (description.isBlank()) {
            descriptionErrorLabel.text = "A descrição não pode estar vazia."

            descriptionErrorLabel.isManaged = true
            descriptionErrorLabel.isVisible = true

            return false
        }

        descriptionErrorLabel.isManaged = false
        descriptionErrorLabel.isVisible = false

        return true
    }

    private fun createButtonRow(): HBox {
        val spacer = Region()

        HBox.setHgrow(
            spacer,
            Priority.ALWAYS
        )

        return HBox(
            8.0,
            spacer,
            cancelButton,
            confirmButton
        )
    }

    private fun closeWindow() {
        scene?.window?.let {
            (it as Stage).close()
        }
    }
}
