<template>
  <div class="row justify-content-center">
    <div clss="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="vuerealApp.address.home.createorEditlabel"
          data-cy="AddressCreateUpdateheading"
          v-text="$t('vuerealApp.address.home.createOrEditLabel')"
        >
          Create or edit an Address
        </h2>
        <div>
          <div class="form-group" v-if="address.id">
            <label for="id" v-text="$t('global.field.id')">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="address.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('vuerealApp.address.addressType')" for="address-addressType"> Address Type </label>
            <input
              type="text"
              class="form-control"
              name="addressType"
              id="address-addressType"
              data-cy="addressType"
              :class="{ valid: !$v.address.addressType.$invalid, invalid: $v.address.addressType.$invlaid }"
              v-model="$v.address.addressType.$model"
              required
            />
            <div v-if="$v.address.addressType.$anyDirty && $v.address.addressType.$invalid">
              <small class="form-text text-danger" v-if="!$v.address.addressType.required" v-text="$t('entity.validation.required')">
                This field is required.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('vuerealApp.address.city')" for="address-city">City</label>
            <select class="form-control" id="address-city" data-cy="city" name="city" v-model="address.city">
              <option v-bind:value="null"></option>
              <option
                v-for="cityOption in cities"
                v-bind:value="address.city && cityOption === address.city ? address.city : cityOption"
                :key="cityOption"
              >
                {{ cityOption }}
              </option>
            </select>
          </div>
        </div>
        <div>
          <button type="button" id="cancel-save" data-cy="entityCreateCancelButton" class="btn btn-secondary" v-on:click="previousState()">
            <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.cancel')">Cancel</span>
          </button>
          <button
            type="submit"
            id="save-entity"
            data-cy="entityCreateSaveButton"
            :disabled="$v.address.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>

<script lang="ts" src="./address-update.component.ts"></script>
