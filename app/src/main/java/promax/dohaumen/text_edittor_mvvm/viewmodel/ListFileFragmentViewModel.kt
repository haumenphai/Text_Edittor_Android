package promax.dohaumen.text_edittor_mvvm.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import promax.dohaumen.text_edittor_mvvm.data.FileTextDatabase
import promax.dohaumen.text_edittor_mvvm.data.FileTextRes
import promax.dohaumen.text_edittor_mvvm.models.FileText
import java.io.File

class ListFileFragmentViewModel: ViewModel() {

    private val fileTexts: MutableLiveData<List<FileText>> = MutableLiveData()
    fun getListFileText(): LiveData<List<FileText>> = fileTexts

    init {
        FileTextDatabase.getINSTANCE().dao().liveData.observeForever {
            it.reverse()
            fileTexts.value = it
        }
    }


    lateinit var onSaveFileTextComple:(mess: String, isSuccess: Boolean) -> Unit
    fun addFileText(fileName: String, content: String) {
        if (fileName.isEmpty()) {
            onSaveFileTextComple("Tên file không được để trống!", false)
        } else {
            val fileText = FileText(fileName, content, "fake date")
            FileTextDatabase.getINSTANCE().dao().insert(fileText)
            onSaveFileTextComple("Lưu file thành công", true)
        }
    }

    fun deleteListChecked(list: List<FileText>, onComplete:() -> Unit = {}) {
        list.forEach { fileText -> FileTextDatabase.getINSTANCE().dao().delete(fileText) }
        onComplete()
    }


    var onRenameComple:(mess: String, isSucssec: Boolean) -> Unit = { mess, isSucssec -> }
    fun reNameFile(newName: String, fileText: FileText) {
        if (newName != "") {
            fileText.name = newName
            FileTextRes.update(fileText)
            onRenameComple("Đổi tên thành công", true)
        } else {
            onRenameComple("Tên file mới không được để trống", false)
        }
    }







}