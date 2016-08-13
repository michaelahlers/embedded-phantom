package ahlers.phantom.embedded;

/**
 * @author [[mailto:michael@ahlers.consulting Michael Ahlers]]
 */
public interface IPhantomConfig<B> {

    Boolean debug();

    B builder();

}
