package fr.unice.vicc;

import java.util.List;
import java.util.Map;

import org.cloudbus.cloudsim.Host;
import org.cloudbus.cloudsim.Pe;
import org.cloudbus.cloudsim.Vm;
import org.cloudbus.cloudsim.VmAllocationPolicy;

public class NoViolationsVmAllocationPolicy extends VmAllocationPolicy {

	public NoViolationsVmAllocationPolicy(List<? extends Host> list) {
		super(list);
	}

	@Override
	public boolean allocateHostForVm(Vm vm) {
		for(Host cHost : getHostList()){
			for (Pe cPe: cHost.getPeList()){
				if(cPe.getPeProvisioner().getAvailableMips() > vm.getMips()){
					if(cHost.vmCreate(vm)){
						//System.out.println("Vm avec id : "+vm.getId()+" créé sur le host avec id : "+cHost.getId());
						return true;
					}
				}
			}
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
