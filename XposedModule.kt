package com.example.appnamechanger

import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import de.robv.android.xposed.IXposedHookLoadPackage
import de.robv.android.xposed.XC_MethodReplacement
import de.robv.android.xposed.callbacks.XC_LoadPackage
import de.robv.android.xposed.XposedHelpers

class XposedModule : IXposedHookLoadPackage {
    override fun handleLoadPackage(lpparam: XC_LoadPackage.LoadPackageParam) {
        if (lpparam.packageName == "com.tencent.mm") {
            XposedHelpers.findAndHookMethod(
                PackageManager::class.java,
                "getApplicationLabel",
                ApplicationInfo::class.java,
                object : XC_MethodReplacement() {
                    override fun replaceHookedMethod(param: MethodHookParam): Any {
                        val appInfo = param.args[0] as ApplicationInfo
                        if (appInfo.packageName == "com.tencent.mm") {
                            return "IGG报警"
                        }
                        return XposedHelpers.callMethod(param.thisObject, "getApplicationLabel", appInfo)
                    }
                }
            )
        }
    }
}