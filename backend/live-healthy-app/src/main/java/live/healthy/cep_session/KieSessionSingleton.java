package live.healthy.cep_session;

import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;

public class KieSessionSingleton {

    private static KieSessionSingleton kieSessionSingletonInstance = null;

    private KieSession kieSession;

    public static KieSessionSingleton getInstance() {
        if (kieSessionSingletonInstance == null) {
            kieSessionSingletonInstance = new KieSessionSingleton();
        }
        return kieSessionSingletonInstance;
    }

    public KieSessionSingleton() {
        KieContainer kieContainer = KieServices.Factory.get().getKieClasspathContainer();
        this.kieSession = kieContainer.newKieSession("intakeSubmitSession");
    }

    public KieSession getKieSession() {
        return kieSession;
    }

    public void setKieSession(KieSession kieSession) {
        this.kieSession = kieSession;
    }
}
