# 🏪 Ekekonomia - Minecraft Economy Mod

Un mod de economía completo para Minecraft 1.21.8 con sistema de tienda personalizado.

## 🎯 Características

### 🛒 Sistema de Tienda
- **GUI Personalizado**: Interfaz de cofre doble con texturas personalizadas
- **Categorías**: 9 categorías de items (Herramientas, Bloques, Comida, etc.)
- **Compra/Venta**: Sistema completo de transacciones
- **Detalle de Items**: Pantalla específica para cada item con controles de cantidad

### 💰 Sistema Económico
- **Monedas**: Sistema de monedas personalizado
- **Precios Dinámicos**: Precios diferentes por categoría
- **Validación**: Verificación de fondos antes de compras
- **Persistencia**: Datos guardados entre sesiones

### 🎮 Controles
- **Tecla B**: Abrir tienda
- **Comando `/tienda`**: Abrir tienda desde chat
- **Click**: Comprar items
- **Shift+Click**: Vender items

## 🚀 Instalación

### Requisitos
- Minecraft 1.21.8
- Fabric Loader 0.17.2+
- Fabric API 0.133.0+

### Pasos
1. Descarga el archivo `.jar` desde [Releases](../../releases)
2. Coloca el archivo en tu carpeta `mods`
3. Inicia Minecraft con Fabric

## 🎨 Texturas Personalizadas

El mod incluye texturas personalizadas para:
- **Tienda Principal**: Layout detallado con categorías y slots
- **Pantalla de Detalle**: Controles de cantidad y botones de confirmación

### Ubicación de Texturas
```
src/main/resources/assets/ekekonomia/textures/gui/
├── shop_background.png (176x276)
└── item_detail_background.png (176x222)
```

## 🔧 Desarrollo

### Estructura del Proyecto
```
src/main/java/com/ekekonomia/
├── client/
│   ├── screen/
│   │   ├── ChestShopScreen.java
│   │   └── ItemDetailScreen.java
│   └── EkekonomiaClientMod.java
├── shop/
│   ├── ChestShopScreenHandler.java
│   ├── ItemDetailScreenHandler.java
│   └── ChestShopScreenHandlerFactory.java
├── commands/
│   └── EconomyCommands.java
└── EkekonomiaMod.java
```

### Compilación
```bash
./gradlew build
```

## 📋 Categorías de Items

1. **Herramientas** - Picos, hachas, palas, etc.
2. **Armas** - Espadas, arcos, etc.
3. **Armadura** - Cascos, pecheras, etc.
4. **Bloques** - Piedra, madera, etc.
5. **Comida** - Pan, carne, etc.
6. **Redstone** - Polvo, antorchas, etc.
7. **Decoración** - Flores, plantas, etc.
8. **Transporte** - Rieles, botes, etc.
9. **Misceláneos** - Otros items

## 🎯 Uso

### Abrir Tienda
- Presiona la tecla **B** en el juego
- O escribe `/tienda` en el chat

### Comprar Items
1. Selecciona una categoría
2. Haz click en un item
3. Ajusta la cantidad (+1, +10, +64)
4. Confirma la compra

### Vender Items
1. Abre la tienda
2. Shift+Click en un item de tu inventario
3. Confirma la venta

## 🐛 Problemas Conocidos

- Las texturas personalizadas pueden no cargar en algunas configuraciones
- El sistema incluye fallback con colores sólidos
- Algunos items pueden no tener precios configurados

## 📝 Licencia

Este proyecto está bajo la licencia MIT. Ver [LICENSE](LICENSE) para más detalles.

## 🤝 Contribuciones

Las contribuciones son bienvenidas. Por favor:
1. Fork el proyecto
2. Crea una rama para tu feature
3. Commit tus cambios
4. Push a la rama
5. Abre un Pull Request

## 📞 Soporte

Si encuentras problemas:
1. Revisa los [Issues](../../issues) existentes
2. Crea un nuevo issue con detalles del problema
3. Incluye logs de error si es posible

## 🎉 Créditos

- **Desarrollado por**: Fernando
- **Versión**: 1.0.0
- **Minecraft**: 1.21.8
- **Fabric**: 0.17.2+