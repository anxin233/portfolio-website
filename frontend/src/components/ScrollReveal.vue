<template>
  <div ref="el" class="scroll-reveal" :class="{ visible }" :style="revealStyle">
    <slot />
  </div>
</template>

<script setup lang="ts">
import { ref, computed, onMounted, onUnmounted } from 'vue'

const props = withDefaults(defineProps<{
  delay?: number
  direction?: 'up' | 'down' | 'left' | 'right'
  distance?: number
}>(), {
  delay: 0,
  direction: 'up',
  distance: 40,
})

const el = ref<HTMLElement>()
const visible = ref(false)
let observer: IntersectionObserver

const revealStyle = computed(() => ({
  transitionDelay: `${props.delay}ms`,
}))

onMounted(() => {
  observer = new IntersectionObserver(
    ([entry]) => {
      if (entry.isIntersecting) {
        visible.value = true
        observer.disconnect()
      }
    },
    { threshold: 0.15 }
  )
  if (el.value) observer.observe(el.value)
})

onUnmounted(() => observer?.disconnect())
</script>

<style scoped>
.scroll-reveal {
  opacity: 0;
  transform: translateY(40px);
  transition: opacity 0.8s cubic-bezier(0.4, 0, 0.2, 1),
              transform 0.8s cubic-bezier(0.4, 0, 0.2, 1);
}

.scroll-reveal.visible {
  opacity: 1;
  transform: translate(0, 0);
}
</style>
