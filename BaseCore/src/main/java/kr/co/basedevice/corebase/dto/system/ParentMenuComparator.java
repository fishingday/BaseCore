package kr.co.basedevice.corebase.dto.system;

import java.util.Comparator;

public class ParentMenuComparator  implements Comparator<ParentMenuDto>{

	@Override
	public int compare(ParentMenuDto o1, ParentMenuDto o2) {
        if (o1.getOrdering() > o2.getOrdering()) {
            return 1;
        } else if (o1.getOrdering() <  o2.getOrdering()) {
            return -1;
        }
        return 0;
	}

}
