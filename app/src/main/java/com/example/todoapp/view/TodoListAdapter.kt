package com.example.todoapp.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.todoapp.R
import com.example.todoapp.databinding.TodoItemLayoutBinding
import com.example.todoapp.model.Todo
import kotlinx.android.synthetic.main.todo_item_layout.view.*

class TodoListAdapter(val todoList:ArrayList<Todo>, val adapterOnClick:(Any) -> Unit):RecyclerView.Adapter<TodoListAdapter.TodoListViewHolder>()
    , TodoCheckedChangeListener, TodoEditClickListener {
    class TodoListViewHolder(var view: TodoItemLayoutBinding):RecyclerView.ViewHolder(view.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoListViewHolder {
        val inflater = LayoutInflater.from(parent.context)
//        val view = inflater.inflate(R.layout.todo_item_layout, parent, false)
        val view = DataBindingUtil.inflate<TodoItemLayoutBinding>(inflater, R.layout.todo_item_layout, parent, false)
        return TodoListViewHolder(view)
    }

    fun updateTodoList(newTodoList:List<Todo>){
        todoList.clear()
        todoList.addAll(newTodoList)
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: TodoListViewHolder, position: Int) {
        holder.view.todo = todoList[position]
        holder.view.listener = this
        holder.view.editlistener = this
    }

    override fun getItemCount(): Int {
        return todoList.size
    }

    override fun onTodoCheckedChange(cb: CompoundButton, isChecked: Boolean, obj: Todo) {
        if(isChecked){
            adapterOnClick(obj)
        }
    }

    override fun onTodoEditClick(v: View) {
        val action = TodoListFragmentDirections.actionEditTodoFragment(v.tag.toString().toInt())
        Navigation.findNavController(v).navigate(action)
    }
}