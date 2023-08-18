package tnt.egts.parser.data.store;

public enum ServiceType {
    AUTH_SERVICE(1),
     TELEDATA_SERVICE(2),
    COMMANDS_SERVICE(3),
    FIRMWARE_SERVICE(4);
    private  final   int srvTypeNo;
  private   ServiceType(int type) {
      this.srvTypeNo =  type;
    }

    public int getSrvTypeNo() {
        return srvTypeNo;
    }
}
