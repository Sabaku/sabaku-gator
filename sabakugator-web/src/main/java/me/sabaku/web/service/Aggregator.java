package me.sabaku.web.service;

import java.util.Collection;

import me.sabaku.api.Person;

/**
 * <pre>
 *             .-._   _ _ _ _ _ _ _ _
 *  .-''-.__.-'00  '-' ' ' ' ' ' ' ' '-.
 * '.___ '    .   .--_'-' '-' '-' _'-' '._
 *  V: V 'vv-'   '_   '.       .'  _..' '.'.
 *    '=.____.=_.--'   :_.__.__:_   '.   : :
 *            (((____.-'        '-.  /   : :
 *  snd                         (((-'\ .' /
 *                            _____..'  .'
 *                           '-._____.-
 * 
 * </pre>
 */
public interface Aggregator {
	Person aggregatePerson(Collection<Person> person);
}
