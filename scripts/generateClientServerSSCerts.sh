#!/bin/bash
# $1 : server name
# $2 : password

echo $1 $2
#
echo generate server key store
#
keytool -genkey -alias $1_server -keyalg RSA -dname "CN=$1,OU=Unit,O=Organization,L=City,S=State,C=US" -keypass $2 -keystore $1_server.jks -storepass $2
#
echo  generate client key store
#
keytool -genkey -alias $1_client -keystore $1_client.p12 -storetype pkcs12 -keyalg RSA -dname "CN=$1_client,OU=Unit,O=Organization,L=City,S=State,C=US" -keypass $2 -storepass $2
#
echo export client cert
#
keytool -exportcert -alias $1_client -file $1_client.cer -keystore $1_client.p12 -storetype pkcs12 -storepass $2
#
echo import client cert to server trust store
#
keytool -importcert -keystore $1_server_trust_store.jks -alias $1 -file $1_client.cer -v -trustcacerts -noprompt -storepass $2

#
echo export server cert
#
keytool -exportcert -alias $1_server -file $1_server.crt -keystore $1_server.jks -storetype jks -storepass $2

#
echo import server cert to client trust store
#

keytool -importcert -keystore $1_client_trust_store.jks -alias $1 -file $1_server.crt -v -trustcacerts -noprompt -storepass $2

#
echo list certs server trust store
#

keytool -list -v -keystore $1_server_trust_store.jks -storepass $2

#
echo list certs client trust store
#

keytool -list -v -keystore $1_client_trust_store.jks -storepass $2
