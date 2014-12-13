package fr.unice.vicc;

import java.util.List;
import java.util.Map;

import org.cloudbus.cloudsim.Host;
import org.cloudbus.cloudsim.Vm;
import org.cloudbus.cloudsim.VmAllocationPolicy;

public class LoadBalanceVmAllocationPolicy extends VmAllocationPolicy {

	public LoadBalanceVmAllocationPolicy(List<? extends Host> list) {
		super(list);
	}

	@Override
	public boolean allocateHostForVm(Vm vm) {
		double bestMIPSHost = 0;
		Host bestHost = null;
		for(Host cHost : getHostList()){
			double cMIPSHost = cHost.getAvailableMips();
			if(cMIPSHost > bestMIPSHost){
				bestMIPSHost = cMIPSHost;
				bestHost = cHost;
			}
		}
		if(bestHost.vmCreate(vm)){
			System.out.println("Vm avec id : "+vm.getId()+" créé sur le host avec id : "+bestHost.getId());
			return true;
		}	
		return false;
	}

	@Override
	public boolean allocateHostForVm(Vm vm, Host host) {
		return host.vmCreate(vm);
	}

	@Override
	public void deallocateHostForVm(Vm vm) {
		vm.getHost().vmDestroy(vm);
	}

	@Override
	public Host getHost(Vm vm) {
		return vm.getHost();
	}

	@Override
	public Host getHost(int vmId, int userId) {
		for(Host cHost : getHostList()){
			if(cHost.getVm(vmId, userId) != null){
				return cHost;
			}
		}
		return null;
	}

	@Override
	public List<Map<String, Object>> optimizeAllocation(List<? extends Vm> vmList) {
		// TODO Auto-generated method stub
		return null;
	}

}
