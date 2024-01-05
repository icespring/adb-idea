package com.developerphil.adbidea.adb.command

import com.android.ddmlib.IDevice
import com.developerphil.adbidea.adb.AdbUtil
import com.developerphil.adbidea.adb.command.receiver.GenericReceiver
import com.developerphil.adbidea.ui.NotificationHelper
import com.intellij.openapi.project.Project
import org.jetbrains.android.facet.AndroidFacet
import java.util.concurrent.TimeUnit

class EnableImeCommand : Command {
    override fun run(context: CommandContext): Boolean {
        try {
            if (AdbUtil.isAppInstalled(context.device, context.packageName)) {

                context.device.executeShellCommand(
                    "ime enable ${context.packageName}/.ImeService",
                    GenericReceiver(),
                    15L,
                    TimeUnit.SECONDS
                )
                Thread.sleep(100)
                context.device.executeShellCommand("ime set ${context.packageName}/.ImeService", GenericReceiver(), 15L, TimeUnit.SECONDS)
                NotificationHelper.info(String.format("<b>%s</b> set ime for app on %s", context.packageName, context.device.name))
                return true
            } else {
                NotificationHelper.error(String.format("<b>%s</b> is not installed on %s", context.packageName, context.device.name))
            }
        } catch (e1: Exception) {
            NotificationHelper.error("Set Ime Enable failed... " + e1.message)
        }
        return false
    }
}