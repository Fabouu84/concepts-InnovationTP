package fr.unice.vicc;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.math3.analysis.function.Power;
import org.cloudbus.cloudsim.Host;
import org.cloudbus.cloudsim.Vm;
import org.cloudbus.cloudsim.VmAllocationPolicy;
import org.cloudbus.cloudsim.power.PowerHost;

public class StatEnergyVmAllocationPolicy extends VmAllocationPolicy {
	private List<PowerHost> list;
	List<PowerHost> sortedList;

	public StatEnergyVmAllocationPolicy(List<PowerHost> list) {
		super(list);
		this.list = new ArrayList<PowerHost>();
		this.list.addAll(list);
		this.sortedList = trierListParPower(this.list);
	}

	@Override
	public boolean allocateHostForVm(Vm vm) {	
		for(PowerHost cPowerHost : sortedList){
			if(cPowerHost.vmCreate(vm)){
				//System.out.println("Vm avec id : "+vm.getId()+" créé sur le host avec id : "+cPowerHost.getId());
				return true;
			}		
		}
		return false;
	}
	
	/**
	 * Méthode qui permet de trier une liste de PowerHost en fonction de son
	 * getPower() de plus petit au plus grand
	 * 
	 * @param list
	 * @return List<PowerHost>
	 */
	public List<PowerHost> trierListParPower(List<PowerHost> list){
		ArrayList<PowerHost> sortedList = new ArrayList<PowerHost>();
		while(!list.isEmpty()){
			PowerHost minPowerHost = null;
			int indexMinPowerHost = -1;
			for(int i=0;i<list.size();i++){
				PowerHost cPowerHost = list.get(i);
				if(minPowerHost == null){
					minPowerHost = cPowerHost;
					indexMinPowerHost = i;
				}
				else{
					if(cPowerHost.getPower() < minPowerHost.getPower()){
						minPowerHost = cPowerHost;
						indexMinPowerHost = i;
					}
				}
			}
			sortedList.add(minPowerHost);
			list.remove(indexMinPowerHost);
		}
		return sortedList;
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