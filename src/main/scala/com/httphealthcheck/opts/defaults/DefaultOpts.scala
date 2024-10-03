package com.httphealthcheck.opts.defaults

import com.httphealthcheck.actorsystem.GlobalActorSystem

trait DefaultOpts extends GlobalActorSystem with DefaultTimer with DefaultHandler with DefaultProcessor {

}
