package com.sedmelluq.discord.lavaplayer.jdaudp;

import com.sedmelluq.discord.lavaplayer.udpqueue.natives.UdpQueueManager;
import net.dv8tion.jda.audio.factory.IAudioSendSystem;
import net.dv8tion.jda.audio.factory.IPacketProvider;

import java.net.DatagramPacket;

public class NativeAudioSendSystem implements IAudioSendSystem, net.dv8tion.jda.core.audio.factory.IAudioSendSystem {
  private final long queueKey;
  private final NativeAudioSendFactory audioSendSystem;
  private final UltimatePacketProvider packetProvider;

  public NativeAudioSendSystem(long queueKey, NativeAudioSendFactory audioSendSystem, IPacketProvider packetProvider) {
    this(queueKey, audioSendSystem, new UltimatePacketProvider(packetProvider));
  }

  public NativeAudioSendSystem(long queueKey, NativeAudioSendFactory audioSendSystem, net.dv8tion.jda.core.audio.factory.IPacketProvider packetProvider) {
    this(queueKey, audioSendSystem, new UltimatePacketProvider(packetProvider));
  }

  private NativeAudioSendSystem(long queueKey, NativeAudioSendFactory audioSendSystem, UltimatePacketProvider ultimatePacketProvider) {
    this.queueKey = queueKey;
    this.audioSendSystem = audioSendSystem;
    this.packetProvider = ultimatePacketProvider;
  }

  @Override
  public void start() {
    audioSendSystem.addInstance(this);
  }

  @Override
  public void shutdown() {
    audioSendSystem.removeInstance(this);
  }

  void populateQueue(UdpQueueManager queueManager) {
    int remaining = queueManager.getRemainingCapacity(queueKey);
    boolean emptyQueue = queueManager.getCapacity() - remaining > 0;

    for (int i = 0; i < remaining; i++) {
      DatagramPacket packet = packetProvider.getNextPacket(emptyQueue);

      if (packet == null || !queueManager.queuePacket(queueKey, packet)) {
        break;
      }
    }
  }

  //encapsulate the mismatching package names of the PackageProvider classes used in JDA and JDA-Audio
  private static class UltimatePacketProvider {

    private IPacketProvider packetProvider0;
    private net.dv8tion.jda.core.audio.factory.IPacketProvider packetProvider1;

    public UltimatePacketProvider(IPacketProvider packetProvider) {
      this.packetProvider0 = packetProvider;
    }
    public UltimatePacketProvider(net.dv8tion.jda.core.audio.factory.IPacketProvider packetProvider) {
      this.packetProvider1 = packetProvider;
    }

    public DatagramPacket getNextPacket(boolean changeTalking) {
      if (packetProvider0 != null) {
        return packetProvider0.getNextPacket(changeTalking);
      } else {
        return packetProvider1.getNextPacket(changeTalking);
      }
    }
  }
}
