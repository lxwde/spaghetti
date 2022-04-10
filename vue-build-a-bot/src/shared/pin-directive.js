/* eslint-disable */
function applyStyle(element, binding) {
  // console.log('arg:', binding.arg, 'modifiers:', binding.modifiers);
  Object.keys(binding.value).forEach((position) => {
    element.style[position] = binding.value[position];
  });
  // if (binding.arg !== 'position') return;

  // Object.keys(binding.modifiers).forEach((key) => {
  //   console.log(key);
  //   element.style[key] = '5px';
  // });
  element.style.position = 'absolute';
  // element.style.bottom = '5px';
  // element.style.right = '5px';
}

export default {
  beforeMount(element, binding) {
    applyStyle(element, binding);
  },
  updated(element, binding) {
    applyStyle(element, binding);
  },
  // created, mounted, beforeUnmount, unmounted
};

/* shorthand for beforeMount and updated
export default function(element, binding) {
  applyStyle(element, binding);
}
*/
