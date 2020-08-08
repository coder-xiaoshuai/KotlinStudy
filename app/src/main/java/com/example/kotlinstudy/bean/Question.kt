package com.example.kotlinstudy.bean

class Question {
    /**
     * apkLink :
     * audit : 1
     * author : xiaoyang
     * canEdit : false
     * chapterId : 440
     * chapterName : 官方
     * collect : false
     * courseId : 13
     * desc :
     *
     *每次新建项目，我们都会生成build.gradle，如果是app模块则会引入：
     * <pre>`apply plugin: 'com.android.application'
    `</pre> *
     * 如果是lib：
     * <pre>`apply plugin: 'com.android.library'
    `</pre> *
     * 问题来了：
     *
     *  1. apply plugin: &#39;com.android.application&#39;背后的原理是？
     *
     * descMd :
     * envelopePic :
     * fresh : false
     * id : 14500
     * link : https://wanandroid.com/wenda/show/14500
     * niceDate : 2020-07-27 01:14
     * niceShareDate : 2020-07-26 11:54
     * origin :
     * prefix :
     * projectLink :
     * publishTime : 1595783646000
     * realSuperChapterId : 439
     * selfVisible : 0
     * shareDate : 1595735648000
     * shareUser :
     * superChapterId : 440
     * superChapterName : 问答
     * tags : [{"name":"本站发布","url":"/article/list/0?cid=440"},{"name":"问答","url":"/wenda"}]
     * title : 每日一问 | apply plugin: &#39;com.android.application&#39; 背后发生了什么？
     * type : 1
     * userId : 2
     * visible : 1
     * zan : 20
     */
    var apkLink: String? = null
    var audit = 0
    var author: String? = null
    var isCanEdit = false
    var chapterId = 0
    var chapterName: String? = null
    var isCollect = false
    var courseId = 0
    var desc: String? = null
    var descMd: String? = null
    var envelopePic: String? = null
    var isFresh = false
    var id = 0
    var link: String? = null
    var niceDate: String? = null
    var niceShareDate: String? = null
    var origin: String? = null
    var prefix: String? = null
    var projectLink: String? = null
    var publishTime: Long = 0
    var realSuperChapterId = 0
    var selfVisible = 0
    var shareDate: Long = 0
    var shareUser: String? = null
    var superChapterId = 0
    var superChapterName: String? = null
    var title: String? = null
    var type = 0
    var userId = 0
    var visible = 0
    var zan = 0
    var tags: List<TagsBean>? =
        null

    class TagsBean {
        /**
         * name : 本站发布
         * url : /article/list/0?cid=440
         */
        var name: String? = null
        var url: String? = null

    }
}