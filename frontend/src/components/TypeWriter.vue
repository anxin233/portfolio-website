<template>
  <span class="typewriter">
    <span class="typed-text">{{ displayText }}</span>
    <span class="cursor" :class="{ blink: !isTyping }">|</span>
  </span>
</template>

<script setup lang="ts">
import { ref, onMounted, onUnmounted } from 'vue'

const props = withDefaults(defineProps<{
  texts: string[]
  typeSpeed?: number
  deleteSpeed?: number
  pauseTime?: number
}>(), {
  typeSpeed: 80,
  deleteSpeed: 40,
  pauseTime: 2000,
})

const displayText = ref('')
const isTyping = ref(true)
let timeoutId: number

function sleep(ms: number) {
  return new Promise<void>(resolve => {
    timeoutId = window.setTimeout(resolve, ms)
  })
}

async function typeLoop() {
  let idx = 0
  while (true) {
    const text = props.texts[idx % props.texts.length]
    isTyping.value = true

    for (let i = 0; i <= text.length; i++) {
      displayText.value = text.slice(0, i)
      await sleep(props.typeSpeed)
    }

    isTyping.value = false
    await sleep(props.pauseTime)
    isTyping.value = true

    for (let i = text.length; i >= 0; i--) {
      displayText.value = text.slice(0, i)
      await sleep(props.deleteSpeed)
    }

    await sleep(300)
    idx++
  }
}

onMounted(() => typeLoop())
onUnmounted(() => clearTimeout(timeoutId))
</script>

<style scoped>
.typewriter {
  display: inline;
}

.typed-text {
  background: var(--gradient-main);
  -webkit-background-clip: text;
  -webkit-text-fill-color: transparent;
  background-clip: text;
}

.cursor {
  display: inline-block;
  margin-left: 2px;
  color: var(--accent-cyan);
  font-weight: 300;
}

.cursor.blink {
  animation: blink 1s step-end infinite;
}

@keyframes blink {
  0%, 100% { opacity: 1; }
  50% { opacity: 0; }
}
</style>
