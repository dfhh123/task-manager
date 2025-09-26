// Система плагинов для модульного фронтенда

export interface Plugin {
  id: string;
  name: string;
  version: string;
  dependencies?: string[];
  routes?: PluginRoute[];
  components?: PluginComponent[];
  hooks?: PluginHook[];
  initialize: () => Promise<void>;
  destroy?: () => void;
}

export interface PluginRoute {
  path: string;
  component: React.ComponentType;
  exact?: boolean;
}

export interface PluginComponent {
  name: string;
  component: React.ComponentType;
  placement: 'header' | 'sidebar' | 'footer' | 'content';
}

export interface PluginHook {
  name: string;
  hook: () => any;
}

class PluginManager {
  private plugins: Map<string, Plugin> = new Map();
  private routes: PluginRoute[] = [];
  private components: Map<string, PluginComponent[]> = new Map();

  async registerPlugin(plugin: Plugin): Promise<void> {
    // Проверяем зависимости
    if (plugin.dependencies) {
      for (const dep of plugin.dependencies) {
        if (!this.plugins.has(dep)) {
          throw new Error(`Dependency ${dep} not found for plugin ${plugin.id}`);
        }
      }
    }

    // Инициализируем плагин
    await plugin.initialize();

    // Регистрируем плагин
    this.plugins.set(plugin.id, plugin);

    // Добавляем маршруты
    if (plugin.routes) {
      this.routes.push(...plugin.routes);
    }

    // Добавляем компоненты
    if (plugin.components) {
      for (const component of plugin.components) {
        if (!this.components.has(component.placement)) {
          this.components.set(component.placement, []);
        }
        this.components.get(component.placement)!.push(component);
      }
    }

    console.log(`Plugin ${plugin.name} registered successfully`);
  }

  unregisterPlugin(pluginId: string): void {
    const plugin = this.plugins.get(pluginId);
    if (!plugin) {
      throw new Error(`Plugin ${pluginId} not found`);
    }

    // Удаляем компоненты
    if (plugin.components) {
      for (const component of plugin.components) {
        const components = this.components.get(component.placement);
        if (components) {
          const index = components.findIndex(c => c.name === component.name);
          if (index !== -1) {
            components.splice(index, 1);
          }
        }
      }
    }

    // Удаляем маршруты
    this.routes = this.routes.filter(route => 
      !plugin.routes?.some(pRoute => pRoute.path === route.path)
    );

    // Уничтожаем плагин
    if (plugin.destroy) {
      plugin.destroy();
    }

    this.plugins.delete(pluginId);
    console.log(`Plugin ${pluginId} unregistered`);
  }

  getRoutes(): PluginRoute[] {
    return [...this.routes];
  }

  getComponents(placement: string): PluginComponent[] {
    return this.components.get(placement) || [];
  }

  getPlugin(pluginId: string): Plugin | undefined {
    return this.plugins.get(pluginId);
  }

  getAllPlugins(): Plugin[] {
    return Array.from(this.plugins.values());
  }
}

export const pluginManager = new PluginManager();
