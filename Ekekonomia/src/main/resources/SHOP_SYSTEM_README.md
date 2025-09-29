# 🏪 Sistema de Tienda - GUI de Cofre

## 🎯 **¿Qué es esto?**
Un sistema de tienda completo que funciona como los plugins de servidores de Minecraft, usando el GUI nativo de cofre con items que se pueden comprar y vender.

## 🚀 **Características**

### ✅ **GUI de Cofre Nativo:**
- **Interfaz familiar** como los plugins de servidores
- **3 filas superiores** para items de la tienda
- **3 filas inferiores** para inventario del jugador
- **Hotbar** integrada del jugador

### ✅ **Sistema de Compra/Venta:**
- **Click izquierdo** en items de la tienda = Comprar
- **Click derecho** en items de la tienda = Vender
- **Tooltips** con precios de compra y venta
- **Saldo** mostrado en la interfaz

### ✅ **Integración Completa:**
- **Sistema de economía** persistente
- **Categorías** de items organizadas
- **Precios** configurables por item
- **Comandos** funcionales

## 🎮 **Cómo Usar**

### **Comandos:**
- `/tienda` - Abrir la tienda
- `/balance` - Ver tu saldo
- `/buy <item> <cantidad>` - Comprar items
- `/sell <item> <cantidad>` - Vender items

### **Teclas:**
- **Tecla B** - Abrir tienda rápidamente

### **En la Tienda:**
1. **Click izquierdo** en cualquier item de la tienda para comprarlo
2. **Click derecho** en cualquier item de la tienda para venderlo
3. **Hover** sobre items para ver precios
4. **Saldo** se muestra en la parte superior

## 🎨 **Interfaz Visual**

### **Layout del Cofre:**
```
┌─────────────────────────────────┐
│ 🏪 Tienda Ekekonomia            │
│ Saldo: 1000 monedas             │
├─────────────────────────────────┤
│ [Item1] [Item2] [Item3] ...     │ ← Fila 1 (Tienda)
│ [Item4] [Item5] [Item6] ...     │ ← Fila 2 (Tienda)
│ [Item7] [Item8] [Item9] ...     │ ← Fila 3 (Tienda)
├─────────────────────────────────┤
│ [Inv1]  [Inv2]  [Inv3]  ...    │ ← Fila 4 (Jugador)
│ [Inv4]  [Inv5]  [Inv6]  ...    │ ← Fila 5 (Jugador)
│ [Inv7]  [Inv8]  [Inv9]  ...    │ ← Fila 6 (Jugador)
├─────────────────────────────────┤
│ [H1] [H2] [H3] [H4] [H5] ...   │ ← Hotbar
└─────────────────────────────────┘
```

### **Tooltips de Items:**
```
§6Nombre del Item
§aComprar: §f2 monedas
§cVender: §f1 moneda
§7Click izquierdo: Comprar
§7Click derecho: Vender
```

## 🔧 **Configuración**

### **Precios de Items:**
Los precios se configuran en `ShopCategory.java`:
```java
BLOQUES.items.put(Items.STONE, new ShopItem(Items.STONE, 2, 1)); // Comprar: 2, Vender: 1
BLOQUES.items.put(Items.WOOD, new ShopItem(Items.WOOD, 1, 0));   // Comprar: 1, Vender: 0
```

### **Categorías Disponibles:**
- **BLOQUES** - Piedra, madera, etc.
- **HERRAMIENTAS** - Picos, hachas, etc.
- **COMIDA** - Manzanas, pan, etc.
- **DECORACIÓN** - Flores, pinturas, etc.

## 🎯 **Ventajas del Sistema**

### ✅ **Estabilidad:**
- **Sin errores** de textura o compatibilidad
- **GUI nativo** de Minecraft
- **Funciona** en cualquier versión

### ✅ **Familiaridad:**
- **Como plugins** de servidores
- **Interfaz intuitiva** para jugadores
- **Comportamiento esperado**

### ✅ **Funcionalidad:**
- **Compra/venta** instantánea
- **Saldo** en tiempo real
- **Tooltips** informativos
- **Integración** completa

## 🚀 **Instalación**

1. **Compila** el mod: `./gradlew build`
2. **Instala** el mod en tu servidor
3. **Usa** `/tienda` para abrir la tienda
4. **Disfruta** del sistema de economía

## 🎉 **¡Listo!**

Ahora tienes un sistema de tienda completo que:
- ✅ **Funciona** como los plugins de servidores
- ✅ **Usa** el GUI nativo de cofre
- ✅ **Es estable** y sin errores
- ✅ **Se integra** perfectamente con tu mod
- ✅ **Es fácil** de usar y configurar

¡El sistema está listo para usar! 🎮
