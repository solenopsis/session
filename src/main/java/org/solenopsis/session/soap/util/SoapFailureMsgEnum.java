/* Copyright (C) 2023 Scot P. Floess
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
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.solenopsis.session.soap.util;

/**
 *
 * @author sfloess
 */
public enum SoapFailureMsgEnum {
    INVALID_SESSION_ID("INVALID_SESSION_ID"),
    SERVER_UNAVAILABLE("SERVER_UNAVAILABLE"),
    FUNCTIONALITY_TEMPORARILY_UNAVAILABLE("FUNCTIONALITY_TEMPORARILY_UNAVAILABLE"),
    INVALID_QUERY_LOCATOR("INVALID_QUERY_LOCATOR");

    private final String msg;

    private SoapFailureMsgEnum(final String msg) {
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }
}
