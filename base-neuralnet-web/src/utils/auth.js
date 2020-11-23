import Cookies from 'js-cookie';

const TokenKey = 'token';

export function getToken(token) {
  return Cookies.get(token);
}

export function setToken(token) {
  return Cookies.set(TokenKey, token);
}

export function removeToken(token) {
  Cookies.set(token, '', { expires: 0 });
  // return Cookies.remove(token);
}
