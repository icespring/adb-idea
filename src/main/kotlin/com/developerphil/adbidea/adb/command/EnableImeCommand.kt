package com.developerphil.adbidea.adb.command

import com.android.ddmlib.IDevice
import com.developerphil.adbidea.adb.AdbUtil
import com.developerphil.adbidea.adb.command.receiver.GenericReceiver
import com.developerphil.adbidea.ui.NotificationHelper
import com.intellij.openapi.project.Project
import org.jetbrains.android.facet.AndroidFacet
import java.util.concurrent.TimeUnit

class EnableImeCommand : Command {
    override fun run(project: Project, device: IDevice, facet: AndroidFacet, packageName: String): Boolean {
        try {
            if (AdbUtil.isAppInstalled(device, packageName)) {
                device.executeShellCommand("ime enable $packageName/.ImeService", GenericReceiver(), 15L, TimeUnit.SECONDS)
                Thread.sleep(100)
                device.executeShellCommand("ime set $packageName/.ImeService", GenericReceiver(), 15L, TimeUnit.SECONDS)
                NotificationHelper.info(String.format("<b>%s</b> set ime for app on %s", packageName, device.name))
                return true
            } else {
                NotificationHelper.error(String.format("<b>%s</b> is not installed on %s", packageName, device.name))
            }
        } catch (e1: Exception) {
            NotificationHelper.error("Set Ime Enable failed... " + e1.message)
        }
        return false
    }
}