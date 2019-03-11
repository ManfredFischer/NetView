package de.netview.dao;

import java.util.List;

public interface ISoftwareDistributedDao {
	List getSoftwareDistributedByHardware();
	List getSoftwareDistributed();
	void deletSoftwareDistribute();
	void putSoftwareDistribute();
}
