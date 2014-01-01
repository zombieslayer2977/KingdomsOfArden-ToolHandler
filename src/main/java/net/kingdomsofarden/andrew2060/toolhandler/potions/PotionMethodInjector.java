//package net.kingdomsofarden.andrew2060.toolhandler.potions;
//import java.lang.reflect.Method;
//
//import org.bukkit.craftbukkit.v1_7_R1.entity.CraftLivingEntity;
//import org.bukkit.entity.LivingEntity;
//import org.bukkit.potion.PotionEffect;
//import org.bukkit.potion.PotionEffectType;
//
//import javassist.util.proxy.MethodFilter;
//import javassist.util.proxy.MethodHandler;
//import javassist.util.proxy.ProxyFactory;
//
//public class PotionMethodInjector {
//    private PotionEffectManager pEMan;
//    public PotionMethodInjector(PotionEffectManager pEMan) {
//        this.pEMan = pEMan;
//    }
//    
//    
//    protected final Object createProxy() {
//        final ProxyFactory pFactory = new ProxyFactory();
//        pFactory.setSuperclass(CraftLivingEntity.class);
//        pFactory.setInterfaces(new Class[] {LivingEntity.class});
//        pFactory.setFilter(new MethodFilter() {
//
//            @Override
//            public boolean isHandled(Method m) {
//                Boolean handle = false;
//                if(m.getName().equalsIgnoreCase("addPotionEffect") || m.getName().equalsIgnoreCase("removePotionEffect")) {
//                    handle = true;
//                }
//                return handle;
//            }
//            
//        });
//        final MethodHandler handler = createDefaultMethodHandler();
//        try {
//            return pFactory.create(new Class<?>[] {PotionEffect.class,Boolean.class}, new Object[]{}, handler);
//        } catch (final Exception e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
//    private MethodHandler createDefaultMethodHandler() {
//        return new MethodHandler() {
//            public final Object invoke(final Object self, final Method thisMethod, final Method proceed, final Object[] args) throws Throwable {
//                switch(thisMethod.getName().toLowerCase()) {
//                
//                case "addPotionEffect": {
//                    try {
//                        PotionEffect pE = (PotionEffect) args[0];
//                        pEMan.addPotionEffectStacking(pE, (LivingEntity)self, false);
//                    } catch (ClassCastException e) {
//                        System.out.println("Someone attempted to trigger a potion effect addition using invalid arguments!");
//                        e.printStackTrace();
//                    }
//                    return true;
//                }
//                case "removePotionEffect": {
//                    try {
//                        PotionEffectType toRemove = (PotionEffectType) args[0];
//                        pEMan.removePotionEffect(toRemove, (LivingEntity)self);
//                        return proceed.invoke(self, args);
//                    } catch (ClassCastException e) {
//                        System.out.println("Someone attempted to trigger a potion effect removal using invalid arguments!");
//                        e.printStackTrace();
//                    }
//                }
//                
//                }
//                return thisMethod.invoke(self, args);
//            }
//        };
//    }
//}