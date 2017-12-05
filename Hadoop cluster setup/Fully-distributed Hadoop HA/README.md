Config file changes for fully-distributed hadoop HA  

We have configured the HA cluster with the following instances each running the respective set of hadoop daemons.  
NameNode 1 (Active NameNode) - NameNode daemon , ZKFC daemon, ZK Quorum  
NameNode 2 (Standby NameNode) -  NameNode daemon, ZKFC daemon, ZK Quorum  
JournalNode - JournalNode daemon, Resource Manager daemon, Node Manager daemon,  ZK Quorum, Map Reduce Job History Server  
Data Node 1 - DataNode daemon, ZK Quorum  
Data Node 2 - DataNode daemon, ZK Quorum     
Data Node 3 - DataNode daemon, ZK Quorum  
