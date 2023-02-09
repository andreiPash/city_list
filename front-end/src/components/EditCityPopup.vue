<template>
  <div class="popup">
    <div class="popup-inner">
      <div class="popup-content">
        <slot/>
      </div>
      <div>
        <button class="popup-update">Update</button>
        <button class="popup-cancel" @click="TogglePopup()">Cancel</button>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "EditCityPopup",
  props: ['TogglePopup']
}
</script>

<style lang="scss" scoped>
.popup {
  position: fixed;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  z-index: 99;
  background-color: rgba(0, 0, 0, 0.2);

  display: flex;
  align-items: center;
  justify-content: center;

  .popup-inner {
    background: #FFFFFF;
    padding: 32px;
    display: grid;
  }

  .popup-content {
    display: grid;
  }
}
</style>