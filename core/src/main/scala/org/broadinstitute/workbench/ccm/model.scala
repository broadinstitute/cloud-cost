package org.broadinstitute.workbench.ccm

import java.time.Instant


final case class Cpu(asString: String) extends AnyVal
final case class CpuNumber(asInt: Int) extends AnyVal
final case class BootDiskSizeGb(asInt: Int) extends AnyVal
final case class Ram(asString: String) extends AnyVal
final case class WorkflowId(id: String) extends AnyVal
final case class DiskName(asString: String) extends AnyVal
final case class DiskSize(asInt: Int) extends AnyVal
final case class DiskType(asString: String) extends AnyVal
final case class Preemptible(asInt: Int) extends AnyVal
final case class Attempt(asInt: Int) extends AnyVal
final case class Region(asString: String) extends AnyVal
final case class Status(asString: String) extends AnyVal
final case class MachineType(asString: String) extends AnyVal
final case class ExecutionEventDescription(asString: String) extends AnyVal
final case class BackEnd(asString: String) extends AnyVal



final case class ExecutionEvent(description: ExecutionEventDescription,
                                startTime: Instant,
                                endTime: Instant)

final case class Call(runtimeAttributes: RuntimeAttributes,
                      executionEvents: List[ExecutionEvent],
                      isCallCaching: Boolean,
                      preemptible: Boolean,
                      region: Region,
                      status: Status,
                      machineType: MachineType,
                      backend: BackEnd,
                      attempt: Attempt)

final case class MetadataResponse(calls: List[Call], startTime: Instant, endTime: Instant)


final case class RuntimeAttributes(cpuNumber: CpuNumber,
                                   disks: Disks,
                                   bootDiskSizeGb: BootDiskSizeGb,
                                   preemptible: Preemptible)

final case class Disks(diskName: DiskName, diskSize: DiskSize, diskType: DiskType)
final case class Compute(cpu: Cpu, ram: Ram)
