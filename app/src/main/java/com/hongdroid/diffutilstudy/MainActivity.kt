package com.hongdroid.diffutilstudy

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity(), CommonListener {
    private var arrayList: ArrayList<UserModel> = arrayListOf(
        UserModel("이홍철", 28, "컴퓨터 게임"),
        UserModel("이홍철", 29, "농구"),
        UserModel("이홍철", 30, "축구"),
        UserModel("이홍철", 31, "야구"),
        UserModel("이홍철", 32, "사격"),
        UserModel("이홍철", 33, "공부"),
        UserModel("이홍철", 34, "프로그래밍")
    )

    private lateinit var adapter: UserAdapter
    private lateinit var mListener: CommonListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mListener = this
        val recyclerView = findViewById<RecyclerView>(R.id.rv_user)
        adapter = UserAdapter(arrayList)
        adapter.setListener(mListener)
        recyclerView.adapter = adapter
    }

    /**
     * 새로운 아이템을 추가한다.
     */
    fun addNewItem(view: View) {
        val newList = ArrayList<UserModel>().apply {
            addAll(arrayList)
            add(UserModel("TEST", 100, "hobby"))
        }

        adapter.changeList(newList)
        arrayList = newList
    }

    /**
     * 선택된 아이템을 제거한다.
     */
    private fun deleteItem(clickPosition: Int) {
        val pos = clickPosition
        val newList = ArrayList<UserModel>().apply {
            addAll(arrayList)
            remove(arrayList[clickPosition])
        }
        adapter.changeList(newList)
        arrayList = newList
        Toast.makeText(this, "선택된 아이템이 제거 되었습니다.",Toast.LENGTH_SHORT).show()
    }

    /**
     * 선택된 아이템을 수정한다.
     */
    private fun modifyItem(clickPosition: Int, name: String, age: Int, hobby: String) {
        val newList = ArrayList<UserModel>().apply {
            addAll(arrayList)
            set(clickPosition, UserModel(name, age, hobby))
        }
        adapter.changeList(newList)
        arrayList = newList
        Toast.makeText(this, "선택된 아이템이 수정 되었습니다.",Toast.LENGTH_SHORT).show()
    }

    /**
     * (수정, 삭제) 중 선택하는 Optional Dialog
     */
    private fun setChooseOptionDialog(clickPosition: Int) {
        val view = layoutInflater.inflate(R.layout.dialog_choose, null)
        val btnModify = view.findViewById<Button>(R.id.btn_modify)
        val btnRemove = view.findViewById<Button>(R.id.btn_remove)

        val builder = AlertDialog.Builder(this)
        builder.setTitle("원하는 옵션을 선택해주세요")
        builder.setView(view)
        val dialog = builder.create()
        dialog.show()

        btnModify.setOnClickListener {
            // 수정
            setModifyDialog(clickPosition)
            dialog.dismiss()
        }

        btnRemove.setOnClickListener {
            // 삭제
            deleteItem(clickPosition)
            dialog.dismiss()
        }

    }


    /**
     * 수정 Dialog
     */
    private fun setModifyDialog(clickPosition: Int) {
        val view = layoutInflater.inflate(R.layout.dialog_modify, null)
        val etName = view.findViewById<EditText>(R.id.et_name)
        val etAge = view.findViewById<EditText>(R.id.et_age)
        val etHobby = view.findViewById<EditText>(R.id.et_hobby)

        AlertDialog.Builder(this)
            .setTitle("수정할 내용을 작성하세요")
            .setPositiveButton("수정완료", DialogInterface.OnClickListener { dialogInterface, i ->
                if (etName.text.isEmpty() || etAge.text.isEmpty() || etHobby.text.isEmpty()) {
                    Toast.makeText(this, "빈 입력 값을 채워주세요!",Toast.LENGTH_SHORT).show()
                    return@OnClickListener
                }


                modifyItem(clickPosition, etName.text.toString(), Integer.parseInt(etAge.text.toString()), etHobby.text.toString())
            })
            .setView(view)
            .show()
    }

    /**
     * 리사이클러 뷰 어댑터 클릭 인터페이스
     */
    override fun onItemClick(position: Int) {
        setChooseOptionDialog(position)
    }

}