#!/usr/bin/env python2
# -*- coding: utf-8 -*-
"""
Created on Thu Jun 28 19:12:52 2018

@author: pi
"""

import psycopg2
from config import config

def connect():
    """ Connect to the PostgresSQL database server"""
    conn= None
    try:
        #read connection parameters
        params=config()
        
        #connect to the PostgreSQL server
        print('Connecting to the PostgreSQL database...')
        conn=psycopg2.connect(**params)
        
        #create a cursor
        cur=conn.cursor()
        
        #execute a statement
        print('PostgreSQL database version:')
        cur.execute('SELECT version()')
        
        #display the PostgreSQL with the PostgreSQL
        cur.close()
    except (Exception, psycopg2.DatabaseError) as error:
        print(error)
    finally:
        if conn is not None:
            print('Database connection closed.')
            
            
if __name__== '__main__':
    connect()
     
    
