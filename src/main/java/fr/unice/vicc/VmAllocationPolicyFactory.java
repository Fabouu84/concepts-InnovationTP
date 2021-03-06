package fr.unice.vicc;

import org.cloudbus.cloudsim.VmAllocationPolicy;
import org.cloudbus.cloudsim.power.PowerHost;

import java.util.List;

/**
 * @author Fabien Hermenier
 */
public class VmAllocationPolicyFactory {

    VmAllocationPolicy make(String id, List<PowerHost> hosts) {
        switch (id) {
            case "naive": return new NaiveVmAllocationPolicy(hosts);
            case "antiaffinity": return new AntiAffinityVmAllocationPolicy(hosts);
            case "loadbalance": return new LoadBalanceVmAllocationPolicy(hosts);
            case "noviolations": return new NoViolationsVmAllocationPolicy(hosts);
            case "statenergy": return new StatEnergyVmAllocationPolicy(hosts);
        }
        throw new IllegalArgumentException("No such policy '" + id + "'");
    }
}
