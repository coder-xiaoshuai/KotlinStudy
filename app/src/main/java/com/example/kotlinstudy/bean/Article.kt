package com.example.kotlinstudy.bean

class Article {
    /**
     * apkLink :
     * audit : 1
     * author : 鸿洋
     * canEdit : false
     * chapterId : 408
     * chapterName : 鸿洋
     * collect : false
     * courseId : 13
     * desc :
     * descMd :
     * envelopePic :
     * fresh : false
     * id : 14622
     * link : https://mp.weixin.qq.com/s/lazQtIa139UQmyhCVG5-gQ
     * niceDate : 1天前
     * niceShareDate : 20小时前
     * origin :
     * prefix :
     * projectLink :
     * publishTime : 1596470400000
     * realSuperChapterId : 407
     * selfVisible : 0
     * shareDate : 1596554197000
     * shareUser :
     * superChapterId : 408
     * superChapterName : 公众号
     * tags : [{"name":"公众号","url":"/wxarticle/list/408/1"}]
     * title : Android MotionLayout动画：续写ConstraintLayout新篇章
     * type : 0
     * userId : -1
     * visible : 1
     * zan : 0
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
    var tags: List<TagsBean>? = null

    class TagsBean {
        /**
         * name : 公众号
         * url : /wxarticle/list/408/1
         */
        var name: String? = null
        var url: String? = null

    }
}