package com.example.dessertclicker.ui

import androidx.lifecycle.ViewModel
import com.example.dessertclicker.data.Datasource.dessertList
import com.example.dessertclicker.data.DessertUiState
import com.example.dessertclicker.determineDessertToShow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class DessertViewModel: ViewModel() {

    private val _uiState = MutableStateFlow(DessertUiState())
    val uiState: StateFlow<DessertUiState> = _uiState.asStateFlow()

    fun onDessertClicked() {
        _uiState.update { currentState ->
            val dessertsSold = currentState.dessertsSold.inc()
            val nextDessertIndex = determineDessertIndex(dessertsSold)
            currentState.copy(
                currentDessertIndex = nextDessertIndex,
                revenue = currentState.revenue + currentState.currentDessertPrice,
                dessertsSold = dessertsSold,
                currentDessertImageId = dessertList[nextDessertIndex].imageId,
                currentDessertPrice = dessertList[nextDessertIndex].price
            )
        }
    }

    private fun determineDessertIndex(dessertsSold: Int): Int {
        var dessertIndex = 0
        for (index in dessertList.indices) {
            if (dessertList[index].startProductionAmount <= dessertsSold) {
                dessertIndex = index
            } else {
                break
            }
        }
        return dessertIndex
    }
}