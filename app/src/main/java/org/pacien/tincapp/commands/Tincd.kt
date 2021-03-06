/*
 * Tinc App, an Android binding and user interface for the tinc mesh VPN daemon
 * Copyright (C) 2017-2018 Pacien TRAN-GIRARD
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package org.pacien.tincapp.commands

import org.pacien.tincapp.context.AppPaths

/**
 * @author pacien
 */
object Tincd {
  fun start(netName: String, deviceFd: Int, ed25519PrivateKeyFd: Int? = null, rsaPrivateKeyFd: Int? = null) =
    Executor.forkExec(Command(AppPaths.tincd().absolutePath)
      .withOption("no-detach")
      .withOption("config", AppPaths.confDir(netName).absolutePath)
      .withOption("pidfile", AppPaths.pidFile(netName).absolutePath)
      .withOption("logfile", AppPaths.logFile(netName).absolutePath)
      .withOption("option", "DeviceType=fd")
      .withOption("option", "Device=$deviceFd")
      .apply { if (ed25519PrivateKeyFd != null) withOption("option", "Ed25519PrivateKeyFile=/proc/self/fd/$ed25519PrivateKeyFd") }
      .apply { if (rsaPrivateKeyFd != null) withOption("option", "PrivateKeyFile=/proc/self/fd/$rsaPrivateKeyFd") })
}
