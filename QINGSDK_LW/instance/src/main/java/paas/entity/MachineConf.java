package paas.entity;

/**
 * @author wzl
 */
public class MachineConf implements Cloneable {
    private int cpu;
    private int memory;
    private int count;
    private int instance_class;
    private int volume_class;
    private int volume_size;

    public int getCpu() {
        return cpu;
    }

    public void setCpu(int cpu) {
        this.cpu = cpu;
    }

    public int getMemory() {
        return memory;
    }

    public void setMemory(int memory) {
        this.memory = memory;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getInstance_class() {
        return instance_class;
    }

    public void setInstance_class(int instance_class) {
        this.instance_class = instance_class;
    }

    public int getVolume_class() {
        return volume_class;
    }

    public void setVolume_class(int volume_class) {
        this.volume_class = volume_class;
    }

    public int getVolume_size() {
        return volume_size;
    }

    public void setVolume_size(int volume_size) {
        this.volume_size = volume_size;
    }

    @Override
    public MachineConf clone() throws CloneNotSupportedException {
        return (MachineConf) super.clone();
    }

    public MachineConf() {
    }
    public MachineConf(int cpu, int memory, int count, int instance_class, int volume_class, int volume_size) {
        this.cpu = cpu;
        this.memory = memory;
        this.count = count;
        this.instance_class = instance_class;
        this.volume_class = volume_class;
        this.volume_size = volume_size;
    }
}
