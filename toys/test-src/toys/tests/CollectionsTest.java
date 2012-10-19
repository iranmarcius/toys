package toys.tests;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;

public class CollectionsTest {

	public static void main(String[] args) {
		List<String> systemRoles = new ArrayList<String>();
		systemRoles.add("role1");
		systemRoles.add("role2");
		systemRoles.add("role3");
		systemRoles.add("role4");
		
		List<String> userRoles = new ArrayList<String>();
		userRoles.add("role1");
		userRoles.add("role3");
		
		System.out.println(CollectionUtils.intersection(userRoles, systemRoles));
		System.out.println(CollectionUtils.containsAny(userRoles, systemRoles));
	}
}
