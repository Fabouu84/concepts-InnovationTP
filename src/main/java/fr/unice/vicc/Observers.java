package fr.unice.vicc;

import java.util.List;

import org.cloudbus.cloudsim.power.PowerHost;

/**
 * Just a container to declare your home-made observers.
 *
 * @see fr.unice.vicc.PeakPowerObserver for a sample observer
 * @author Fabien Hermenier
 */
public class Observers {

    /**
     * Build all the observers.
     */
    public void build(List<PowerHost> hosts) {
    	//AntiAffinityObserver antiAffinityObserver = new AntiAffinityObserver(hosts);
    	LoadBalanceObserver loadBalanceObserver = new LoadBalanceObserver(hosts);
    }
}
