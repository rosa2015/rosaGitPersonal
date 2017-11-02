package com.zhtx.goodsentity.common;

import org.springframework.web.servlet.ModelAndView;

/**
 * Created by maqian on 16/4/25.
 */
public class ActionResult {

    public static JsonResult Json(int code, String msg, Object data) {

        return new JsonResult(code, msg, data);
    }

    public static JsonResult Json(int code, String msg) {
        return Json(code, msg, null);
    }

    public static JsonResult Json(int code) {
        return Json(code, null);
    }

    public static JsonResult Json(Object obj) {
        if (obj == null) {
            return Json(-1, null, obj);
        }
        return Json(1, null, obj);
    }

    public static String JsonStr(int code, String msg, Object data) {

        return new JsonResult(code, msg, data).toJsonString();
    }

    public static String JsonStr(int code, String msg) {
        return Json(code, msg, null).toJsonString();
    }

    public static String JsonStr(int code) {
        return Json(code, null).toJsonString();
    }

    public static String JsonStr(Object obj) {
        if (obj == null) {
            return Json(-1, null, obj).toJsonString();
        }
        return Json(1, null, obj).toJsonString();
    }
    public static ModelAndView Redirect(String url) {
        return new ModelAndView("redirect:" + url);
    }
}
