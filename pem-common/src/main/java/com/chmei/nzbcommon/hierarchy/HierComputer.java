package com.chmei.nzbcommon.hierarchy;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * copy from RoleHierarchyImpl
 * @author lixinjie
 * @since 2018-10-22
 * 
 * @see org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl
 */
public class HierComputer<T> implements IHierComputer<T> {

	private Collection<T> direct;
	private Collection<? extends IHierarchy<T>> allHierarchy;
	
	private Map<T, Set<T>> objectsReachableInOneStepMap;
	private Map<T, Set<T>> objectsReachableInOneOrMoreStepsMap;
	
	private Collection<T> reachable;
	private Collection<T> allReachable;
	
	public HierComputer(Collection<T> direct, Collection<? extends IHierarchy<T>> allHierarchy) {
		this.direct = direct;
		this.allHierarchy = allHierarchy;
		buildAllReachable();
	}
	
	@Override
	public Collection<T> getDirect() {
		return direct;
	}

	@Override
	public Collection<? extends IHierarchy<T>> getAllHierarchy() {
		return allHierarchy;
	}
	
	@Override
	public Collection<T> getReachable() {
		return reachable;
	}

	@Override
	public Collection<T> getAllReachable() {
		return allReachable;
	}
	
	private void buildAllReachable() {
		buildObjectsReachableInOneStepMap();
		buildObjectsReachableInOneOrMoreStepsMap();
		
		reachable = new HashSet<>();
		allReachable = new HashSet<>();
		for (T object : direct) {
			allReachable.add(object);
			Set<T> additionalReachable = 
					objectsReachableInOneOrMoreStepsMap.get(object);
			if (additionalReachable != null) {
				reachable.addAll(additionalReachable);
				allReachable.addAll(additionalReachable);
			}
		}
	}

	private void buildObjectsReachableInOneStepMap() {
		objectsReachableInOneStepMap = new HashMap<>();
		for (IHierarchy<T> hierarchy : allHierarchy) {
			T higherObject = hierarchy.getHigher();
			T lowerObject = hierarchy.getLower();
			
			Set<T> lowerObjectsReachableInOneStepSet;
			if (objectsReachableInOneStepMap.containsKey(higherObject)) {
				lowerObjectsReachableInOneStepSet =
					objectsReachableInOneStepMap.get(higherObject);
			} else {
				lowerObjectsReachableInOneStepSet = new HashSet<>();
				objectsReachableInOneStepMap.put(higherObject,
					lowerObjectsReachableInOneStepSet);
			}
			lowerObjectsReachableInOneStepSet.add(lowerObject);
		}
	}
	
	private void buildObjectsReachableInOneOrMoreStepsMap() {
		objectsReachableInOneOrMoreStepsMap = new HashMap<>();

		for (T object : objectsReachableInOneStepMap.keySet()) {
			Set<T> objectsToVisitSet = new HashSet<>(
					objectsReachableInOneStepMap.get(object));
			
			Set<T> visitedObjectsSet = new HashSet<>();

			while (!objectsToVisitSet.isEmpty()) {
				T aObject = objectsToVisitSet.iterator().next();
				objectsToVisitSet.remove(aObject);
				visitedObjectsSet.add(aObject);
				if (objectsReachableInOneStepMap.containsKey(aObject)) {
					Set<T> newReachableObjects =
							objectsReachableInOneStepMap.get(aObject);

					if (objectsToVisitSet.contains(object)
							|| visitedObjectsSet.contains(object)) {
						throw new IllegalStateException("cycle appears in hierarchy compute.");
					} else {
						objectsToVisitSet.addAll(newReachableObjects);
					}
				}
			}
			objectsReachableInOneOrMoreStepsMap.put(object, visitedObjectsSet);
		}

	}
}
