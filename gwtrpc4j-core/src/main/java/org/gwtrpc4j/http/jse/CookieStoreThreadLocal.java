package org.gwtrpc4j.http.jse;

/**
 * 
 * CookieStoreThreadLocal is a thread local cookie manager. CookieStore is only
 * available in java 6.0
 * 
 * @deprecated gwtrpc4j must be compatible java 1.5
 * @author npeters
 * 
 */
@Deprecated
public class CookieStoreThreadLocal {
	/**
	 * implements CookieStore { static ThreadLocal<CookieStore> threadLocal =
	 * new ThreadLocal<CookieStore>();
	 * 
	 * public static List<HttpCookie> getCurrentCookies() { return
	 * getInnerCookieStore().getCookies(); }
	 * 
	 * private static CookieStore getInnerCookieStore() { CookieStore
	 * innerCookieStore = threadLocal.get(); if (innerCookieStore == null) {
	 * innerCookieStore = new InMemoryCookieStore();
	 * threadLocal.set(innerCookieStore); } return innerCookieStore; }
	 * 
	 * public void add(URI arg0, HttpCookie arg1) {
	 * getInnerCookieStore().add(arg0, arg1); }
	 * 
	 * public List<HttpCookie> get(URI arg0) { return
	 * getInnerCookieStore().get(arg0); }
	 * 
	 * public List<HttpCookie> getCookies() { return
	 * getInnerCookieStore().getCookies(); }
	 * 
	 * public List<URI> getURIs() { return getInnerCookieStore().getURIs(); }
	 * 
	 * public boolean remove(URI arg0, HttpCookie arg1) { return
	 * getInnerCookieStore().remove(arg0, arg1); }
	 * 
	 * public boolean removeAll() { return getInnerCookieStore().removeAll(); }
	 **/
}
