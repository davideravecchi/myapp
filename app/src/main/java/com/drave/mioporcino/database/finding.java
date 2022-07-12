package com.drave.mioporcino.database;

public class finding {

    public int id=0;
    public long time=0;                 // Timestamp UTC (millisecondi) , data ritrovamento
    public int typefinding=0;           // 0=nessun 1=fungaia 2=auto 3 parcheggio 4=ristorante
    public long time_zone_offset=0;     // Timezone offset (millisecondi)
    public long time_dst_offset=0;      // DST offset (millisecondi)
    public long lasttime_finding=0;     // Timestamp UTC (millisecondi) , data ultimo ritrovamento
    public int last_quantity=0;         // numero oggetti ultimo ritrovamento
    public int valutation_place=0;      // da 0=niente,1=scarsa,2=discreta,3=buona,4=ottima
    public long gpstime=0;              // Timestamp (millisecondi)
    public String location = "";        // Paese
    public String mushroom_type = "";   // Tipo fungo
    public Double latitude=0.0;
    public Double longitude=0.0;
    public String note="";             //note riguardanti ritrovamento

}
