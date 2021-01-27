<template>
  <div v-if="!item.hidden">
    <template v-if="hasOneShowingChild(item.children, item)">
      <app-link v-if="onlyOneChild.meta" :to="resolvePath(onlyOneChild.path)">
        <el-menu-item :index="resolvePath(onlyOneChild.path)" :class="{ 'submenu-title-noDropdown': !isNest }">
          <item :icon="onlyOneChild.meta.icon || (item.meta && item.meta.icon)" :title="generateTitle(onlyOneChild.meta.title)" />
        </el-menu-item>
      </app-link>
    </template>

    <el-submenu v-else ref="subMenu" :index="resolvePath(item.path)" popper-append-to-body>
      <template slot="title">
        <item v-if="item.meta" :icon="item.meta && item.meta.icon" :title="generateTitle(item.meta.title)" />
      </template>
      <sidebar-item v-for="child in item.children" :key="child.path" :is-nest="true" :item="child" :base-path="resolvePath(child.path)" class="nest-menu" />
    </el-submenu>
  </div>
</template>

<script>
import path from 'path'
import { generateTitle } from '@/utils/i18n'
import { isExternal } from '@/utils/validate'
import Item from './Item'
import AppLink from './Link'
import FixiOSBug from './FixiOSBug'

export default {
  name: 'SidebarItem',
  components: { Item, AppLink },
  mixins: [FixiOSBug],
  props: {
    // route object
    item: {
      type: Object,
      required: true,
    },
    isNest: {
      type: Boolean,
      default: false,
    },
    basePath: {
      type: String,
      default: '',
    },
  },
  data() {
    this.onlyOneChild = null
    return {}
  },
  methods: {
    // 是否有一个需要显示的路由
    hasOneShowingChild(children = [], parent) {
      const showingChildren = children.filter((item) => {
        // 过滤出需要显示的路由
        if (item.hidden) {
          return false
        } else {
          // 在只有一个显示子路由的情况下能用到
          this.onlyOneChild = item
          return true
        }
      })
      // 当只有一个子路由
      if (showingChildren.length === 1) {
        // 有孙子路由时
        if (showingChildren[0].children && showingChildren[0].children.length) {
          return false
        }
        // 没有孙子路由 但是alwaysShow属性为真值时
        if (parent.alwaysShow) {
          return false
        }
        return true
      }

      // 完全没有子路由
      if (showingChildren.length === 0) {
        this.onlyOneChild = { ...parent, path: '' }
        return true
      }
      return false
    },
    resolvePath(routePath) {
      if (isExternal(routePath)) {
        return routePath
      }
      if (isExternal(this.basePath)) {
        return this.basePath
      }
      return path.resolve(this.basePath, routePath)
    },

    generateTitle,
  },
}
</script>
