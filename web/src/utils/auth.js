/*
 * @Author: zyk
 * @Date: 2020-07-22 10:55:29
 * @Last Modified by:   zyk
 * @Last Modified time: 2020-07-22 10:55:29
 */
import Cookies from 'js-cookie'

const TokenKey = 'Admin-Token'

export function getToken() {
  return Cookies.get(TokenKey)
}

export function setToken(token) {
  return Cookies.set(TokenKey, token)
}

export function removeToken() {
  return Cookies.remove(TokenKey)
}
