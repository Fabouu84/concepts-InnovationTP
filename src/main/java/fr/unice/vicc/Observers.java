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
    	//Commenter ici les observers que l'on ne souhaite pas exécuter
    	AntiAffinityObserver antiAffinityObserver = new AntiAffinityObserver(hosts);
    	LoadBalanceObserver loadBalanceObserver = new LoadBalanceObserver(hosts);
    }
}
