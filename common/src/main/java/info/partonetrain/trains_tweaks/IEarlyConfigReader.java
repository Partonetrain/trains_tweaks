package info.partonetrain.trains_tweaks;

public interface IEarlyConfigReader {
    public void readConfigsEarly();

    //if true, config is parsed during a static initialization in a mixin
    public default boolean isExtraEarly(){
        return false;
    }
}
