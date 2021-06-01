create user bmcuser@localhost identified by 'bmc@2018net';

grant select,insert,update,delete on bmc.* to bmcuser@localhost;

flush privileges;