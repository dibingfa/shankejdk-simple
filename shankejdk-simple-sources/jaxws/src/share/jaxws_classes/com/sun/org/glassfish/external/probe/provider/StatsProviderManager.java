/*
 * Copyright (c) 2009, 2010, Oracle and/or its affiliates. All rights reserved.
 */



package com.sun.org.glassfish.external.probe.provider;

import java.util.Vector;

/**
 *
 * @author abbagani
 */
public class StatsProviderManager {

   private StatsProviderManager(){
   }


   public static boolean register(String configElement, PluginPoint pp,
                                    String subTreeRoot, Object statsProvider) {
        return (register(pp, configElement, subTreeRoot, statsProvider, null));
   }

   public static boolean register(PluginPoint pp, String configElement,
                                  String subTreeRoot, Object statsProvider,
                                  String invokerId) {
        StatsProviderInfo spInfo =
            new StatsProviderInfo(configElement, pp, subTreeRoot, statsProvider, invokerId);

        return registerStatsProvider(spInfo);
   }

   public static boolean register(String configElement, PluginPoint pp,
                                    String subTreeRoot, Object statsProvider,
                                    String configLevelStr) {
        return(register(configElement, pp, subTreeRoot, statsProvider, configLevelStr, null));
   }

   public static boolean register(String configElement, PluginPoint pp,
                                    String subTreeRoot, Object statsProvider,
                                    String configLevelStr, String invokerId) {
        StatsProviderInfo spInfo =
            new StatsProviderInfo(configElement, pp, subTreeRoot, statsProvider, invokerId);
        spInfo.setConfigLevel(configLevelStr);

        return registerStatsProvider(spInfo);
   }

   private static boolean registerStatsProvider(StatsProviderInfo spInfo) {
      //Ideally want to start this in a thread, so we can reduce the startup time
      if (spmd == null) {
          //Make an entry into the toBeRegistered map
          toBeRegistered.add(spInfo);
      } else {
          spmd.register(spInfo);
          return true;
      }
       return false;
   }

   public static boolean unregister(Object statsProvider) {
      //Unregister the statsProvider if the delegate is not null
      if (spmd == null) {
          for (StatsProviderInfo spInfo : toBeRegistered) {
              if (spInfo.getStatsProvider() == statsProvider) {
                  toBeRegistered.remove(spInfo);
                  break;
              }
          }

      } else {
          spmd.unregister(statsProvider);
          return true;
      }
       return false;
   }


   public static boolean hasListeners(String probeStr) {
      //See if the probe has any listeners registered
      if (spmd == null) {
          return false;
      } else {
          return spmd.hasListeners(probeStr);
      }
   }


   public static void setStatsProviderManagerDelegate(
                                    StatsProviderManagerDelegate lspmd) {
      //System.out.println("in StatsProviderManager.setStatsProviderManagerDelegate ***********");
      if (lspmd == null) {
          //Should log and throw an exception
          return;
      }

      //Assign the Delegate
      spmd = lspmd;

      //System.out.println("Running through the toBeRegistered array to call register ***********");

      //First register the pending StatsProviderRegistryElements
      for (StatsProviderInfo spInfo : toBeRegistered) {
          spmd.register(spInfo);
      }

      //Now that you registered the pending calls, Clear the toBeRegistered store
      toBeRegistered.clear();
   }

   //variables
   static StatsProviderManagerDelegate spmd; // populate this during our initilaization process
   static Vector<StatsProviderInfo> toBeRegistered = new Vector();
}
