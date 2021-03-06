package com.example.kotlinstudy.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.kotlinstudy.db.bean.TableQuestion

@Dao
interface QuestionDao {

    /**
     * 插入数据 如果冲突则覆盖
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertQuestion(question: TableQuestion)

    /**
     * 插入数据 如果冲突则覆盖
     */
    @Query("select * from question")
    fun getAllQuestion(): List<TableQuestion>?

}