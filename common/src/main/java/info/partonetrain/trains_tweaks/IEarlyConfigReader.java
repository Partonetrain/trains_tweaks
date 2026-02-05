package info.partonetrain.trains_tweaks;

//why does this exist? because Forge Config API doesn't always register configs by the times we need them
public interface IEarlyConfigReader {
    public void readConfigsEarly();

    //if true, config is parsed during a static initialization in a mixin
    public default boolean isExtraEarly(){
        return false;
    }
}
